package com.bitcamp.orl.feed.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bitcamp.orl.feed.domain.Feed;
import com.bitcamp.orl.feed.domain.FeedComment;
import com.bitcamp.orl.feed.domain.FeedGallery;
import com.bitcamp.orl.feed.domain.FeedLikeGallery;
import com.bitcamp.orl.feed.domain.FeedSearchByNickname;
import com.bitcamp.orl.feed.domain.FeedView;
import com.bitcamp.orl.feed.domain.FollowList;
import com.bitcamp.orl.feed.domain.FollowerList;
import com.bitcamp.orl.feed.domain.NewFeedList;
import com.bitcamp.orl.feed.domain.NewFollowList;
import com.bitcamp.orl.member.domain.Member;

public interface FeedDao {

	
//세라
	// 1. follower수 가져오기
	int selectFollowerCount(@Param("memberIdx") int memberIdx);

	// 2. following 수 가져오기
	int selectFollowingCount(@Param("memberIdx") int memberIdx);

	// 3. follower 리스트 가져오기 (팔로우 버튼 없는 기존 버전)
	/* List<FollowList> selectFollowerList(@Param("memberIdx") int memberIdx); */
	
	//3-1 follower 리스트 가져오기 발전(10.08)
	//List<FollowerList> selectFollowerList(@Param("yourIdx") int yourIdx, @Param("myIdx")int myIdx);
	
	//3-2 follower리스트 가져오기 (view 사용) 10.12
	//List<NewFollowList> selectFollowerList(@Param("yourIdx") int yourIdx, @Param("myIdx")int myIdx);
	
	//3-3 프로시져 사용해서 follower 리스트 가져오기 11.02
	List<NewFollowList> selectFollowerList(@Param("yourIdx") int yourIdx, @Param("myIdx")int myIdx);
	

	// 4. following 리스트 가져오기 08.31
	//List<FollowList> selectFollowingList(@Param("memberIdx") int memberIdx);
	
	//4-1 following 리스트 가져오기 +버튼 (10.12) view 사용
	//List<NewFollowList> selectFollowingList(@Param("yourIdx")int yourIdx,@Param("myIdx") int myIdx);
	
	// following list 프로시져 사용
	List<NewFollowList> selectFollowingList(@Param("yourIdx")int yourIdx,@Param("myIdx") int myIdx);

	// 5. 게시물 갯수 가져오기
	int selectFeedCount(@Param("memberIdx") int memberIdx);

	// 6. 남피드 출력 위해 필요한 member 객체 가져오기(한행)
	Member selectOneMember(@Param("memberIdx") int memberIdx);

	// 7. 팔로우하기와 팔로우 끊기 버튼을 위해 나와 남이 팔로우 상태인지 체크하기
	int selectFollowRelation(@Param("myIdx") int myIdx, @Param("yourIdx") int yourIdx);

	// 8. 팔로우 시작하기
	int insertFollowMember(@Param("myIdx") int myIdx, @Param("yourIdx") int yourIdx);

	// 9. 팔로우 그만하기
	int deleteFollowMember(@Param("myIdx") int myIdx, @Param("yourIdx") int yourIdx);

	// 10. 내 피드 기본 정렬 가져오기
	List<FeedGallery> selectFeedGallery(@Param("memberIdx") int memberIdx);

	// 11. 내 피드 좋아요 정렬 가져오기
	List<FeedLikeGallery> selectFeedLikeGallery(@Param("memberIdx") int memberIdx);

	// 12. 좋아요 누른 상태인지 아닌지
	int selectLikeStatus(@Param("myIdx") int myIdx, @Param("boardIdx") int boardIdx);

	// 13. 좋아요 누르기 ->insert
	int insertLike(@Param("memberIdx") int memberIdx, @Param("boardIdx") int boardIdx);

	// 14. 좋아요 취소 ->delete
	int deleteLike(@Param("memberIdx") int memberIdx, @Param("boardIdx") int boardIdx);

	// 15. 피드 상세 보기에 좋아요 갯수(0910)
	int selectTotalLikeCount(@Param("boardIdx") int boardIdx);

	// 16. 피드 인기순 정렬(0910)
	List<NewFeedList> selectFeedOrderByLike();

	// 17. 해시태그 검색 결과 (0911)
	List<NewFeedList> selectByHashtag(@Param("hashtag") String hashtag);

	// 18. 닉네임 검색 결과 (0911)
	List<FeedSearchByNickname> selectByNickname(@Param("nickname") String nickname);

	
	
	
// 우리
	
	// 피드 작성
	int createFeed(Feed feed);

	// 피드 삭제
	int deleteFeed(@Param("memberIdx") int memberIdx, @Param("boardIdx") int boardIdx);

	// 피드 수정
	int editFeed(
			@Param("boardDiscription") String boardDiscription,
			@Param("hashtag") String hashtag,
			@Param("tag") String tag,
			@Param("boardIdx") int boardIdx);

	// 피드 댓글 작성(우리)
	int insertFeedComment(FeedComment feedcomment);
	
	// 피드 댓글 작성(세라 수정) 오버로딩
	int insertFeedComment(@Param("comment")String comment,@Param("boardIdx")int boardIdx,@Param("memberIdx")int memberIdx);


	// 댓글 삭제
	int deleteComment(@Param("boardCommentIdx") int boardCommentIdx);

	// 피드 댓글 리스트
	List<FeedComment> selectFeedComment(@Param("boardIdx") int boardIdx);

	// 피드 상세보기
	FeedView selectFeedView(int boardIdx);

	// 전체 피드 리스트 (최신순)
	List<NewFeedList> selectNewFeed();
	
	// 추가 (09.18.우리)
	// 특정 피드 선택
	Feed selectFeed(@Param("boardIdx") int boardIdx);
	
	// 추가 (09.18.우리)
	// 닉네임 중복 체크
	int selectNickname(@Param("memberNickname") String memberNickname);
	
	// 추가 (09.22.우리)
	// 닉네임으로 memberIdx 찾기
	int selectMemberIdx(@Param("memberNickname") String memberNickname);
	
	// 추가 (09.25.우리)
	// 피드 존재 여부 체크
	int selectFeedChk(@Param("memberIdx") int memberIdx, @Param("boardIdx") int boardIdx);
		



// 관리자용 추가 0915

	// 전체 피드 리스트
	List<FeedView> selectAllFeed();

	// 피드 삭제
	int deleteFeedByAdmin(@Param("boardIdx") int boardIdx);

	// 산별 피드보기(용민)
	List<NewFeedList> selectNewFeedByMountain(@Param("mName") String mName);
}
