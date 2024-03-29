package com.jcw.blog.service;

import com.jcw.blog.auth.PrincipalDetail;
import com.jcw.blog.dto.ReplySaveRequestDto;
import com.jcw.blog.exception.UserNotFoundException;
import com.jcw.blog.model.Board;
import com.jcw.blog.model.Reply;
import com.jcw.blog.model.User;
import com.jcw.blog.repository.BoardRepository;
import com.jcw.blog.repository.ReplyRepository;
import com.jcw.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true) //select 할 때 트랜잭션 시작, 서비스 종료시에 트랜잭셩 종료(정합성 유지)
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;

    @Transactional
    public void 글작성(Board board, User user) {
        System.out.println("BoardService.글작성 호출");
        if (user == null || board == null){
            log.error("UserService.글작성.board :" + board);
            log.error("UserService.글작성.user :" + user);
            throw new IllegalArgumentException("글 작성이 실패했습니다.");
        }

        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);

        System.out.println("BoardService.글작성 끝");
    }

    public Page<Board> 글목록(Pageable pageable) {
        Page<Board> boards = boardRepository.findAll(pageable);

        return boards;
    }

    public Board 글상세보기(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("글 상세보기 실패 : 해당 게시글 id를 찾을 수 없습니다"));
    }

    @Transactional
    public void 글삭제(Long id, User user) {
        Board deleteBoard = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("글 삭제 실패 : 삭제할 글이 없습니다."));

        Long writerId = deleteBoard.getUser().getId();
        Long userId = user.getId();

        if(!writerId.equals(userId)){
            throw new IllegalArgumentException("글 삭제 실패 : 해당 글을 삭제할 권한이 없습니다.");
        }
        boardRepository.delete(deleteBoard);
    }

    @Transactional
    public void 글수정(Long id, Board requestBoard, User user) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("글 수정 실패 : 수정할 글이 없습니다"));

        if(!Objects.equals(board.getUser().getId(), user.getId())){
            throw new IllegalArgumentException("글 수정 실패 : 해당 글을 수정할 권한이 없습니다.");
        }

        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());
        // 해당 함수 종료시(서비스가 종료될 때) 트랜잭션이 종료되면서 더티체킹으로 자동 업데이트가 됨 db flush
    }

    @Transactional
    public void 댓글작성(ReplySaveRequestDto dto) {
//        User user = userRepository.findById(dto.getUserId())
//                .orElseThrow(() -> new IllegalArgumentException("댓글 작성 실패 : 작성자 ID를 찾을 수 없습니다."));
//
//        Board board = boardRepository.findById(dto.getBoardId())
//                .orElseThrow(() -> new IllegalStateException("댓글 작성 실패 : 게시글 ID를 찾을 수 없습니다"));
//
//        Reply reply = Reply.builder()
//                .user(user)
//                .board(board)
//                .content(dto.getContent())
//                .build();
//
//        replyRepository.save(reply);
        replyRepository.mSave(dto.getUserId(), dto.getBoardId(), dto.getContent());
    }

    @Transactional
    public void 댓글삭제(Long replyId, User user) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(()-> new IllegalArgumentException("댓글 삭제 실패 : 해당 댓글을 찾을 수 없습니다."));

        if(!reply.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("댓글 삭제 실패 : 해당 댓글을 삭제할 권한이 없습니다.");
        }
        replyRepository.deleteById(replyId);
    }
}
