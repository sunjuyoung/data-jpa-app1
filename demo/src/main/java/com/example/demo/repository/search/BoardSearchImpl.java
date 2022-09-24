package com.example.demo.repository.search;

import com.example.demo.dto.BoardImageDto;
import com.example.demo.dto.BoardListAllDto;
import com.example.demo.dto.BoardListReplyCountDto;
import com.example.demo.entity.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.entity.QBoard.*;
import static com.example.demo.entity.QReply.*;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch{

    public BoardSearchImpl() {
        super(Board.class);
    }

    @Override
    public Page<BoardListAllDto> searchWithAllByProjection(String[] types, String keyword, Pageable pageable) {
        JPQLQuery<Board> boardJPQLQuery = from(board);
        boardJPQLQuery.leftJoin(reply).on(reply.board.eq(board));

        boardJPQLQuery.groupBy(board);

        JPQLQuery<BoardListAllDto> dtoJPQLQuery =
                boardJPQLQuery.select(Projections.fields(BoardListAllDto.class,
                        board.title,
                        board.boardId,
                        board.username,
                        board.imageSet,
                        board.createdDate
                     //  reply.count().as("replyCount")
                ));

        this.getQuerydsl().applyPagination(pageable,dtoJPQLQuery);
        List<BoardListAllDto> dtoList = dtoJPQLQuery.fetch();
        long count = dtoJPQLQuery.fetchCount();

       // return new PageImpl<>(dtoList,pageable,count);
        return null;
    }

    @Override
    public Page<BoardListAllDto> searchWithAll(String[] types, String keyword, Pageable pageable) {

        JPQLQuery<Board> boardJPQLQuery = from(board);
        boardJPQLQuery.leftJoin(reply).on(reply.board.eq(board));

        boardJPQLQuery.groupBy(board);

        getQuerydsl().applyPagination(pageable,boardJPQLQuery);

        JPQLQuery<Tuple> tupleJPQLQuery = boardJPQLQuery.select(board,reply.countDistinct());

        List<Tuple> tupleList = tupleJPQLQuery.fetch();

        List<BoardListAllDto> dtoList = tupleList.stream().map(tuple -> {
            Board board1 = (Board) tuple.get(board);
            long replyCount = tuple.get(1, Long.class);

            BoardListAllDto dto = BoardListAllDto.builder()
                    .boardId(board1.getBoardId())
                    .title(board1.getTitle())
                    .createdDate(board1.getCreatedDate())
                    .replyCount(replyCount)
                    .username(board1.getUsername())
                    .build();


            //BoardImage
            List<BoardImageDto> boardImageDtos  = board1.getImageSet().stream().sorted().map(boardImage -> {
                BoardImageDto boardImageDto = BoardImageDto.builder()
                        .fileName(boardImage.getFileName())
                        .ord(boardImage.getOrd())
                        .uuid(boardImage.getUuid())
                        .build();
                return boardImageDto;
            }).collect(Collectors.toList());

            dto.setBoardImages(boardImageDtos);

            return dto;
        }).collect(Collectors.toList());


        long totalCount = boardJPQLQuery.fetchCount();

        return new PageImpl<>(dtoList,pageable,totalCount);
    }



    @Override
    public Page<BoardListReplyCountDto> searchWithReplyCount(String[] types, String keyword, Pageable pageable) {

        //
        JPQLQuery<Board> query = from(board);
        query.leftJoin(reply).on(reply.board.eq(board));
        query.groupBy(board);


        //검색조건
        if((types != null && types.length >0) && keyword != null){
            BooleanBuilder builder = new BooleanBuilder();
            for(String type : types){
                switch (type){
                    case "t": builder.or(board.title.containsIgnoreCase(keyword));
                    break;
                    case "c" : builder.or(board.content.containsIgnoreCase(keyword));
                    break;
                    case "w" : builder.or(board.username.containsIgnoreCase(keyword));
                    break;
                }
            }
            query.where(builder);
        }
        query.where(board.boardId.gt(0L));

        //Projections.bean() 이용해서 dto로 처리
        JPQLQuery<BoardListReplyCountDto> dtoJPQLQuery =
                query.select(Projections.bean(BoardListReplyCountDto.class,
                        board.boardId,
                        board.title,
                        board.username,
                        board.createdDate,
                        reply.count().as("replyCount")));

        this.getQuerydsl().applyPagination(pageable,dtoJPQLQuery);
        List<BoardListReplyCountDto> dtoList = dtoJPQLQuery.fetch();
        long count = dtoJPQLQuery.fetchCount();


        return new PageImpl<>(dtoList,pageable,count);
    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {

        JPQLQuery<Board> query = from(board);
        if(types != null && types.length>0 && keyword != null){
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for(String type : types){
                switch (type){
                    case "t": booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c": booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "w" : booleanBuilder.or(board.username.contains(keyword));
                    break;

                }
            }
            query.where(booleanBuilder);
        }
        query.where(board.boardId.gt(0L));
        this.getQuerydsl().applyPagination(pageable,query);
        List<Board> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list,pageable,count);
    }

    @Override
    public Page<Board> search1(Pageable pageable) {

        JPQLQuery<Board> query = from(board);
        query.where(board.title.contains("1"));

        this.getQuerydsl().applyPagination(pageable,query);

        List<Board> fetch = query.fetch();
        long count = query.fetchCount();
        return null;
    }
}
