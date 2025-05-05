package tn.esprit.tic.timeforge.Service;

import tn.esprit.tic.timeforge.Entity.ProjectComment;

public interface IProjectCommentService {
    public ProjectComment createComment(Long projectId, ProjectComment comment);
    public void deleteComment(Long commentId);
}
