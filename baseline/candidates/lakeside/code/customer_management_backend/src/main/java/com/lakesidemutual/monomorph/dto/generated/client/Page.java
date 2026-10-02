package com.lakesidemutual.monomorph.dto.generated.client;

import com.google.protobuf.Any;
import com.lakesidemutual.monomorph.dto.generated.proto.page.PageDTO;
import java.util.List;

/**
 * Auto-generated DTO gRPC client for {@link PageDTO}.
 */
public class Page {
    private PageDTO dtoInstance;

    private Page(PageDTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    /**
     * Constructor matching the original Page API, adapted for DTO storage.
     * The generic element list is represented as a list of {@link Any}.
     */
    public Page(List<Any> elements, int offset, int limit, int size) {
        PageDTO.Builder builder = PageDTO.newBuilder()
                .setOffset(offset)
                .setLimit(limit)
                .setSize(size);
        if (elements != null) {
            builder.addAllElements(elements);
        }
        this.dtoInstance = builder.build();
    }

    public PageDTO toDTO() {
        return this.dtoInstance;
    }

    public static Page fromDTO(PageDTO dtoInstance) {
        return new Page(dtoInstance);
    }

    public List<Any> getElements() {
        return dtoInstance.getElementsList();
    }

    public void setElements(List<Any> elements) {
        PageDTO.Builder builder = dtoInstance.toBuilder().clearElements();
        if (elements != null) {
            builder.addAllElements(elements);
        }
        this.dtoInstance = builder.build();
    }

    public int getOffset() {
        return dtoInstance.getOffset();
    }

    public void setOffset(int offset) {
        this.dtoInstance = dtoInstance.toBuilder().setOffset(offset).build();
    }

    public int getLimit() {
        return dtoInstance.getLimit();
    }

    public void setLimit(int limit) {
        this.dtoInstance = dtoInstance.toBuilder().setLimit(limit).build();
    }

    public int getSize() {
        return dtoInstance.getSize();
    }

    public void setSize(int size) {
        this.dtoInstance = dtoInstance.toBuilder().setSize(size).build();
    }
}