package com.myblog1.payload;


import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PostDto {

    private Long id;
    @Size(min=3,max=20,message = "Should be more than 3 characters")
    private String title;

    private String description;

    private String content;

}