package test;

public class MemberUse {
//	public MemberUse() {
//		Member m = new Member();
//	}  강한 결합
	
	@Autowired
	Member m;
	public MemberUse(Member M) {

	} // 느슨한 결합 Spring에서 사용하는 것
}
