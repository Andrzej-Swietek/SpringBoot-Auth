package pl.swietek.springbootapi.responses.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginatedApiResponse<T> {

    @JsonProperty("data")
    private List<T> data;

    @JsonProperty("links")
    private Links links;

    @JsonProperty("meta")
    private Meta meta;

    @Data
    @AllArgsConstructor
    @Builder
    public static class Links {
        private String first;
        private String last;
        private String prev;
        private String next;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class Meta {
        private int currentPage;
        private int from;
        private int lastPage;
        private String path;
        private int perPage;
        private int to;
        private int total;
    }

    public static <T> PaginatedApiResponse<T> withData(Page<T> page, String baseLink, int currentPage) {
        List<T> element = page.getContent();
        return PaginatedApiResponse.<T>builder()
                .data(element)
                .links(
                        PaginatedApiResponse.Links.builder()
                                .first(baseLink)
                                .last(baseLink + "&page=" + page.getTotalPages())
                                .prev(page.hasPrevious() ? (baseLink + "&page=" + (currentPage - 1)) : null)
                                .next(page.hasNext() ? (baseLink + "&page=" + (currentPage + 1)) : null)
                                .build()
                )
                .meta(
                        PaginatedApiResponse.Meta.builder()
                                .currentPage(page.getNumber() + 1)
                                .from(page.getNumber() * page.getSize())
                                .lastPage(page.getTotalPages())
                                .path( baseLink.split("\\?", 2)[0])
                                .perPage(page.getSize())
                                .to(page.getNumber() * page.getSize() + element.size())
                                .total((int) page.getTotalElements())
                                .build()
                )
                .build();
    }
}
