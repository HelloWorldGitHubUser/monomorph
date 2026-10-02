package com.lakesidemutual.monomorph.dto.generated.client;

import com.google.protobuf.Any;
import com.lakesidemutual.monomorph.dto.generated.proto.page.PageDTO;

import java.util.List;

/**
 * Auto-generated DTO gRPC client
 * {@link Page} and {@link PageDTO}.
 */
public class Page {

    private PageDTO dtoInstance;

    // Private DTO constructor, used by fromDTO/toDTO
    private Page(PageDTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    // Original constructor adapted for DTO storage
    public Page(List<Any> elements, int offset, int limit, int size) {
        this.dtoInstance = PageDTO.newBuilder()
                .addAllElements(elements)
                .setOffset(offset)
                .setLimit(limit)
                .setSize(size)
                .build();
    }

    public PageDTO toDTO() {
        return this.dtoInstance;
    }

    public static Page fromDTO(PageDTO dtoInstance) {
        return new Page(dtoInstance);
    }

    // --- DTO GETTERS ---
    public List<Any> getElements() {
        return dtoInstance.getElementsList();
    }

    public int getOffset() {
        return dtoInstance.getOffset();
    }

    public int getLimit() {
        return dtoInstance.getLimit();
    }

    public int getSize() {
        return dtoInstance.getSize();
    }

    // --- DTO SETTERS ---
    public void setElements(List<Any> elements) {
        this.dtoInstance = dtoInstance.toBuilder()
                .clearElements()
                .addAllElements(elements)
                .build();
    }

    public void setOffset(int offset) {
        this.dtoInstance = dtoInstance.toBuilder()
                .setOffset(offset)
                .build();
    }

    public void setLimit(int limit) {
        this.dtoInstance = dtoInstance.toBuilder()
                .setLimit(limit)
                .build();
    }

    public void setSize(int size) {
        this.dtoInstance = dtoInstance.toBuilder()
                .setSize(size)
                .build();
    }
}
