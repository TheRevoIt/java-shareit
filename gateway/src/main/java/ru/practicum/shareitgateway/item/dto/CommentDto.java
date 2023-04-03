package ru.practicum.shareitgateway.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareitgateway.util.Create;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CommentDto {
    private long id;
    @NotBlank(groups = Create.class)
    @Size(max = 255)
    private String text;
    private LocalDateTime created;
    private String authorName;
}
