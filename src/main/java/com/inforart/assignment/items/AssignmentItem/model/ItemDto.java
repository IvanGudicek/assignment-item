package com.inforart.assignment.items.AssignmentItem.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    private String code;
    private String name;
    private String measurementUnit;

}
