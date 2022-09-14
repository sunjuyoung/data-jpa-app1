package com.example.demo.repository;

import com.example.demo.dto.ReplyDto;
import com.example.demo.entity.QBoard;
import com.example.demo.entity.QReply;
import com.example.demo.entity.Reply;
import com.example.demo.repository.search.BoardSearch;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.example.demo.entity.QBoard.*;
import static com.example.demo.entity.QReply.*;

public class ReplyRepositoryImpl extends QuerydslRepositorySupport implements ReplyCustomRepository {

    public ReplyRepositoryImpl() {
        super(Reply.class);
    }

    @Override
    public List<ReplyDto> getListOfBoard(Long board_id,Pageable pageable) {
        JPQLQuery<Reply> query = from(reply);
        query.innerJoin(reply).on(reply.board.eq(board));
        query.where(board.board_id.eq(board_id));

        JPQLQuery<ReplyDto> dtoJPQLQuery = query.select(Projections.bean(ReplyDto.class,
                reply.reply_id,
                reply.replyer,
                reply.replyText,
                reply.createdDate,
                reply.modifiedDate));

        this.getQuerydsl().applyPagination(pageable,dtoJPQLQuery);
        List<ReplyDto> dtoList = dtoJPQLQuery.fetch();

        return dtoList;
    }
}
