package woori_design_web.backend_woori_design_web.service;

import woori_design_web.backend_woori_design_web.entity.UserLike;
import java.util.List;

public interface UserLikeService {
    // 좋아요 등록
    UserLike addLike(Long userId, Long postId);

    // 좋아요 삭제
    void removeLike(Long userId, Long postId);

    // 특정 컴포넌트의 좋아요 조회
    Long getLikeCountByPostId(Long postId);

}