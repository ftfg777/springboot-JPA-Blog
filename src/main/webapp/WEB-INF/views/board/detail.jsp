<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp" %>


<div class="container">

  <button class="btn btn-secondary" onclick="history.back()">돌아가기</button>
  <c:if test="${board.user.id == principal.user.id}">
    <a href="/board/${board.id}/updateForm" class="btn btn-warning">수정</a>
    <button id="btn-board-delete" class="btn btn-danger">삭제</button>
  </c:if>
  <br><br>
  <div>
      글번호: <span id="id">${board.id}</span>
      작성자: <span>${board.user.username}</span>
      조회수: <span>${board.count}</span>
  </div>

      <div class="form-group">
        <h3>${board.title}</h3>
      </div>
      <hr>
      <div class="form-group">
        <div>${board.content}</div>
      </div>  
    
      <div class="card">
        <div>
          <input type="hidden" id="userId" value="${principal.user.id}"/>
          <div class="card-body"><textarea id="reply-content" class="form-control" rows="1"></textarea></div>
          <div class="card-footer"><button id="btn-reply-save"class="btn btn-primary">등록</button></div>
        </div>
      </div>
      <br>
      <div class="card">
        <div class="card-header">댓글리스트 (${board.replys.size()})</div>
        <ul id="reply--box" class="list-group">
          <c:forEach var="replys" items="${board.replys}">
            <li id="reply--1" class="list-group-item d-flex justify-content-between">
              <div>${replys.content}</div>
              <div class="d-flex">
                <div class="font-italic">${replys.user.username} &nbsp;</div>
                <button type="button" class="btn btn-danger badge">삭제</button>
              </div>
            </li>
          </c:forEach>
        </ul>
      </div>

</div>

<script src="/js/board.js"></script>

<%@ include file="../layout/footer.jsp" %>





