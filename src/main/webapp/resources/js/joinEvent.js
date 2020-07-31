document.querySelector('input[name="email"]').addEventListener('keyup',checkEmailFormat);	// 이메일 형식 체크 함수 호출
document.querySelector('input[name="email"]').addEventListener('blur',checkEmail);			// 이메일 중복 체크 함수 호출
document.getElementById('password').addEventListener('blur',checkPasswordComplexity);		// 비밀번호 형식 체크 함수 호출
document.getElementById('confirmPassword').addEventListener('blur',checkPassword);			// 비밀번호 일치 체크 함수 호출
document.getElementById('yy').addEventListener('blur',checkYear);							// 생년 4자리 확인
