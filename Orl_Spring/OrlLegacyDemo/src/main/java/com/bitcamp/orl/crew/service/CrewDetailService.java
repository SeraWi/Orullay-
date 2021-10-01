package com.bitcamp.orl.crew.service;

import javax.servlet.http.HttpSession;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitcamp.orl.crew.dao.Dao;
import com.bitcamp.orl.crew.domain.Crew;
import com.bitcamp.orl.crew.domain.CrewInfo;
import com.bitcamp.orl.member.domain.MemberDto;

@Service
public class CrewDetailService {

	private Dao dao;

	@Autowired
	private SqlSessionTemplate template;

	// 크루 정보가져오기
	public CrewInfo getCrewInfo(HttpSession session, int crewIdx) {
		dao = template.getMapper(Dao.class);

		CrewInfo crewinfo = getCrew(crewIdx).crewToCrewInfo();
		crewinfo.setCrewMemberNum(getCrewMemberNum(crewIdx));
		crewinfo.setCrewCommentNum(getCrewCommentNum(crewIdx));
		
		MemberDto member = (MemberDto)session.getAttribute("memberVo");
		
		if (member != null) {
			//member 객체 있는 경우 member가 이 크루 가입한 상태인지 확인후 세팅
			crewinfo.setIsReg(getIsCrewMember(member.getMemberIdx(), crewIdx));
		} else {
			//member 객체 없는 경우(회원이 아님) 크루 미가입 상태 ->false
			crewinfo.setIsReg(false);
		}
		crewinfo.setMemberProfile(dao.selectMemberByMemberIdx(getCrew(crewIdx).getMemberIdx()).getMemberProfile());
		crewinfo.setMemberNickName(dao.selectMemberByMemberIdx(crewinfo.getMemberIdx()).getMemberNickname());

		return crewinfo;
	}

	// crewIdx로 한 크루 선택
	public Crew getCrew(int crewIdx) {
		dao = template.getMapper(Dao.class);
		Crew crew = dao.selectCrew(crewIdx);
		return crew;
	}

	// 해당 크루의 크루원 수 계산
	public int getCrewMemberNum(int crewIdx) {
		dao = template.getMapper(Dao.class);
		int crewMemberNum = dao.selectCrewMemberNum(crewIdx);
		return crewMemberNum;
	}

	// 해당 크루의 댓글 수 계산
	public int getCrewCommentNum(int crewIdx) {
		dao = template.getMapper(Dao.class);
		int crewCommentNum = dao.selectCrewCommentNum(crewIdx);
		return crewCommentNum;
	}

	// 접속한사람(세션 값)이 해당 크루의 크루원인지 여부 체크
	public boolean getIsCrewMember(int memberIdx, int crewIdx) {
		boolean chk = false;
		dao = template.getMapper(Dao.class);
		int chkInt = dao.selectCountMemberToRegCrew(memberIdx, crewIdx);
		if (chkInt != 0) {
			// 크루 가입한 상태
			chk = true;
		}
		return chk;
	}
}
