package woori_design_web.backend_woori_design_web.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woori_design_web.backend_woori_design_web.entity.User;
import woori_design_web.backend_woori_design_web.entity.UserLike;
import woori_design_web.backend_woori_design_web.repository.UserLikeRepository;
import woori_design_web.backend_woori_design_web.service.UserLikeService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserLikeServiceImpl implements UserLikeService {
    private final UserLikeRepository userLikeRepository;

    @Override
    @Transactional
    public UserLike addLike(Long userId, Long postId) {
        if (userId == null || postId == null) {
            throw new IllegalArgumentException("User ID 또는 Post ID는 null일 수 없습니다.");
        }
        try {
            UserLike userLike = UserLike.builder()
                    .user(User.builder().id(userId).build())
                    .postId(postId)
                    .createdAt(LocalDateTime.now())
                    .build();
            UserLike savedLike = userLikeRepository.save(userLike);
            log.info("좋아요 등록 성공 - userId: {}, postId: {}", userId, postId);
            return savedLike;
        } catch (Exception e) {
            log.error("좋아요 등록 실패 - userId: {}, postId: {}", userId, postId, e);
            throw new RuntimeException("좋아요 등록 중 오류 발생", e);
        }
    }


    @Override
    @Transactional
    public void removeLike(Long userId, Long postId) {
        if (userId == null || postId == null) {
            throw new IllegalArgumentException("User ID 또는 Post ID는 null일 수 없습니다.");
        }
        try {
            Optional<UserLike> userLike = userLikeRepository.findByUserIdAndPostId(userId, postId);
            if (userLike.isPresent()) {
                userLikeRepository.delete(userLike.get());
                log.info("좋아요 삭제 성공 - userId: {}, postId: {}", userId, postId);
            } else {
                log.warn("좋아요 삭제 실패 - 해당 좋아요가 존재하지 않음 (userId: {}, postId: {})", userId, postId);
                throw new RuntimeException("좋아요 삭제 중 오류 발생");
            }
        } catch (Exception e) {
            log.error("좋아요 삭제 실패 - userId: {}, postId: {}", userId, postId, e);
            throw new RuntimeException("좋아요 삭제 중 오류 발생");
        }
    }


    @Override
    @Transactional(readOnly = true)
    public Long getLikeCountByPostId(Long postId) {
        if (!userLikeRepository.existsByPostId(postId)) {
            log.error("좋아요 개수 조회 실패 - UserLike 테이블에 존재하지 않는 postId: {}", postId);
            throw new EntityNotFoundException("해당 postId에 대한 좋아요 기록이 없습니다.");
        }

        Long likeCount = userLikeRepository.countLikesByPostId(postId);

        if (likeCount == null) {
            log.warn("좋아요 개수 조회 결과가 null - postId: {}", postId);
            return 0L;
        }

        log.info("좋아요 개수 조회 성공 - postId: {}, 좋아요 개수: {}", postId, likeCount);
        return likeCount;
    }


}
