package com.hm.forest.board.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import com.hm.forest.board.model.vo.Board;
import com.hm.forest.board.model.vo.Reply;
import com.hm.forest.common.util.PageInfo;

@Mapper
public interface BoardMapper {

	// 게시판 타입별 게시글 전체 개수
	int selectBoardCountByType(@Param("type") String type);

	// 게시판 타입별 게시글 전체 목록 조회
	List<Board> selectBoardListsByType(@Param("type") String type, RowBounds bounds);
	
	// [검색값 o] 게시판 타입별 게시글 전체 개수
	int selectBoardCountBySearchValue(@Param("type") String type, @Param("searchType") String searchType,@Param("keyWord") String keyWord);
	 
	// [검색값 o] 게시판 타입별 게시글 전체 목록 조회
	List<Board> selectBoardListsBySearchValue(@Param("type") String type, RowBounds bounds, @Param("searchType") String searchType, @Param("keyWord") String keyWord);

	// 특정 게시글 조회
	Board selectBoardByNo(@Param("no") int no);

	// 게시글 등록
	int insertBoard(Board board);

	// 게시글 수정 
	int updateBoard(Board board);

	// 게시글 삭제
	int updateStatus(@Param("no") int no,@Param("status") String status);

	// 게시글 조회수 업데이트
	int updateReadCount(@Param("no") int no);

	// faq 게시판 글 목록 조회
	List<Board> selectFAQListsByType(@Param("type") String type);

	// 댓글 등록
	int insertReply(Reply reply);

	// 댓글 리스트 조회
	List<Reply> selectRepliesByBoardNo(@Param("boardNo") int boardNo);

	// 댓글 수 
	int selectReplyCountByBoardNo(@Param("boardNo") int boardNo);

	// 특정 댓글 조회
	Reply selectReplyByNo(@Param("no") int no);

	// 댓글 삭제
	int updateReplyStatus(@Param("no") int no, @Param("status") String status);

	// 댓글 수정
	int updateReply(Reply reply);

	// 게시글 전체 갯수
	int selectboardcount();
	
	// 게시글 전체 조회
	List<Board> getboardlist(RowBounds rowBounds);
	
	// 검색 게시글 갯수
	int selectboardcountsearch(@Param("searchType") String searchType,@Param("keyWord") String keyWord);
	
	// 검색 게시글 조회
	List<Board> getboardlistsearch(@Param("searchType") String searchType,@Param("keyWord") String keyWord, RowBounds rowBounds);

	// 게시글 삭제
	int updatboardstatus(@Param("status") String status, @Param("no")int no);

	// 게시글 계시
	int activateboard(@Param("status") String status, @Param("no")int no);

	// 댓글 전체 갯수
	int selectreplycount();

	// 댓글 전체 조회
	List<Board> getreplylists(RowBounds rowBounds);

	// 검색 댓글 갯수
	int selectreplycountsearch(@Param("searchType")String searchType);

	// 검색 댓글 조회
	List<Board> getreplylistsearch(@Param("searchType")String searchType, RowBounds rowBounds);


}
