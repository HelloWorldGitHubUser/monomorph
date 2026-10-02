package com.lakesidemutual.monomorph.dto.generated.client;

import com.google.protobuf.Any;
import com.lakesidemutual.monomorph.dto.generated.proto.page.PageDTO;
import java.util.List;

/**
 * Auto-generated DTO gRPC client for {@link PageDTO}.
 * Uses composition to expose the same API as the original Page class,
 * but stores all data in the internal DTO instance.
 */
public class Page {
    private PageDTO dtoInstance;

    /**
     * Private constructor used by {@link #fromDTO(PageDTO)}.
     */
    private Page(PageDTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    /**
     * Constructor compatible with the original Page class signature,
     * adapted for DTO storage.
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

    // --- GETTERS (matching original API) ---

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
}
