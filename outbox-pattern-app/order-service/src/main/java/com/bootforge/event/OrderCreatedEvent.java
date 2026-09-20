package com.bootforge.event;

import lombok.Builder;

@Builder
public record OrderCreatedEvent(
        String aggregatedId,
        String payload
) {
}
