<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp" %>


<div class="container">

    <form method="POST">
      <div class="form-group">
        <label for="title">Title:</label>
        <input type="text" name="title" class="form-control" placeholder="Enter Title" id="title">
      </div>
      <div class="form-group">
        <label for="content">Content:</label>
        <textarea rows="3" cols="" name="content" class="form-control summernote" id="content"></textarea>
      </div>  
    </form>
    
    <button id="btn-board-save" class="btn btn-primary">글쓰기완료</button>
</div>

<script>
    $('.summernote').summernote({
      tabsize: 2,
      height: 300
    });
  </script>
<script src="/js/board.js"></script>

<%@ include file="../layout/footer.jsp" %>





