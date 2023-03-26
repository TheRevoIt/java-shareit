package ru.practicum.shareit.item.model;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.user.model.User;

public class CommentMapper {
    public static Comment dtoToComment(CommentDto commentDto, User user, Item item) {
        return new Comment(
                commentDto.getId(), commentDto.getText(), item, user,
                commentDto.getCreated());
    }

    public static CommentDto commentToDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getText(), comment.getCreated(), comment.getAuthor().getName());
    }
}
