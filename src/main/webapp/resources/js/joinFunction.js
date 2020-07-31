//이메일의 keyup event 발생시 이메일 형식 체크 함수 실행 
function checkEmailFormat(event){		
	const email = document.getElementById('email').value;	// joinForm의 이메일 받아오기
	idmsg = document.getElementById('idmsg');	// 이메일 중복값 확인하는 idmsg
	console.log('email : ' + email);		// 들어오는 이메일값 확인

	const regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;	// 이메일 형식
	if(regExp.test(email) == false){
		idmsg.innerText = '잘못된 이메일 형식입니다';
		idmsg.style.color = 'red';
		event.target.className = 'reborder';
		return false;
	}else{
		return true;
	}
}

//이메일의 blur event 발생 시 이메일 중복 체크 함수 실행
function checkEmail(event){		
	const email = document.getElementById('email').value;
	idmsg = document.getElementById('idmsg'); 
	const request = new XMLHttpRequest();
	request.open('POST', cpath + '/checkEmail/');
	request.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	request.send("email=" + email);
	request.onreadystatechange = function() {
		if(checkEmailFormat() === false)		// 이메일 형식이 맞는지 체크
			return;		
		else {				// 이메일 형식 맞을 경우 밑의 코드 실행
			if(request.readyState == 4 && request.status == 200){
				if(request.responseText === '이미 사용중인 계정입니다'){
					idmsg.innerHTML = request.responseText;
					idmsg.style.color = 'red';
					event.target.className = 'reborder';
				}
				else{
					idmsg.innerHTML = request.responseText;
					event.target.className = '';
					idmsg.style.color = 'blue';
				}
			}
		}
	}
}

// 비밀번호 정규식 체크
//2020.07.31 
//password => Password 수정 / mccheckPasswordComplexity() 함수 추가 / checkPassword 함수 수정
function checkPasswordComplexity(event){
	const regExp = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{6,}$/; // 대소문자, 특수문자, 숫자의 조합으로 6자 이상

	const password = document.getElementById('Password').value;
	const pwmsg = document.getElementById('pwmsg');

	if(regExp.test(password) == false){		// 비밀번호 정규식과 입력값이 안맞는 경우
		pwmsg.innerHTML = '비밀번호는 소문자,대문자,숫자의 조합으로 6이상';
		pwmsg.style.color = 'red';
		event.target.className = 'reborder';
		pwmsg.className = 'error';
	}
	else {
		event.target.className = '';
		pwmsg.className = '';
		pwmsg.innerHTML = '비밀번호 사용 가능';
		pwmsg.style.color = 'blue';
	}
}

function mccheckPasswordComplexity(event){
	const regExp = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{6,}$/; // 대소문자, 특수문자, 숫자의 조합으로 6자 이상
	
	const password = document.getElementById('Password').value;
	const pwmsg = document.getElementById('pwmsg');
	
	console.log('=============' + password.length);
	if(password.length == 0){
		pwmsg.className = '';
		pwmsg.innerHTML = '';
	}
	else if(regExp.test(password) == false){		// 비밀번호 정규식과 입력값이 안맞는 경우
		console.log('=============' + password);
		pwmsg.innerHTML = '비밀번호는 소문자,대문자,숫자의 조합으로 6이상';
		pwmsg.style.color = 'red';
		pwmsg.className = 'error';
	}
	else {
		pwmsg.className = '';
		pwmsg.innerHTML = '비밀번호 사용 가능';
		pwmsg.style.color = 'blue';
	}
}

// 비밀번호, 비밀번호 확인 값 비교
function checkPassword(event) {
	const password = document.getElementById('Password').value;
	const cPassword = document.getElementById('confirmPassword').value;
	const cpwmsg = document.getElementById('cpwmsg');

	if(password.length == 0){
		cpwmsg.innerHTML = '';
		cpwmsg.className = '';
	}
	else if(password === cPassword){
		cpwmsg.innerHTML = '비밀번호 일치';
		cpwmsg.style.color = 'blue';
		event.target.className = '';
		cpwmsg.className = '';
	}
	else{
		document.getElementById('cpwmsg').innerHTML = '비밀번호가 일치하지 않습니다';
		document.getElementById('cpwmsg').style.color = 'red';
		event.target.className = 'reborder';
		cpwmsg.className = 'error';
	}
}

// 태어난 년도 4자리 확인하기
function checkYear(event){
	console.log('checkyyyyyyyy');
	const year = document.getElementById('yy').value;
	const yymsg = document.getElementById('yymsg');
	
	console.log('=========year length : ' + year.length);
	if(year.length != 4){
		yymsg.style.display = 'block';
		yymsg.innerHTML = '태어난 년도 4자리를 입력하세요';
		yymsg.style.color = 'red';
		event.target.className = 'reborder';
	}else{
		event.target.className = '';
		yymsg.style.display = 'none';
	}
}

// 회원가입
function join(){
	inputs = document.querySelectorAll('input');
//	console.log(inputs.length);
	cnt = 0;
	for(i=0; i<inputs.length; i++){
		if(inputs[i].className === 'reborder'){	// input의 기본 클래스이름 = 없음, 입력조건에 안맞을 시 클래스이름 = reborder
//			console.log(inputs[i].className);
			cnt++;								// input 클래스이름이 reborder인 것 카운트 
		}
	}
	if(cnt != 0) {		// cnt가 0이 아닐 경우 reborder클래스가 있는 경우로 보고 submit 안되게 한다
		alert('필수항목을 입력해 주세요	');
		return;	
	}
	else{
		document.getElementById('joinForm').submit();	// 모든 조건 만족 시 joinForm submit
	}
}

//2020.07.31 
//회원정보 수정 버튼 누르고 넘어가기
function mychange(){
	ps = document.querySelectorAll('p');
	cnt = 0;
	for(i=0; i<ps.length; i++){
		if(inputs[i].className === 'error') cnt++;								
	}
	if(cnt != 0) {	
		alert('비밀번호를 확인해 주세요');
		return;	
	}
	else{
		document.getElementById('mychangeForm').submit();	// 모든 조건 만족 시 mychangeForm submit
	}
}

// 이용약관 모두 체크
function checkAll(){
	checks = document.getElementsByName('check');
	console.log(checks.length);
	if(document.getElementById('checkAll').checked){	// 모두체크 체크 시 
		for(i=0; i<checks.length; i++){
			checks[i].checked = true;		// 약관 모두 체크
		}
	}else{
		for(i=0; i<checks.length; i++){
			checks[i].checked = false;		// 모두체크 해제시 체크된 모든 항목 체크 해제
		}
	}
}

// 필수 이용약관 체크 확인 후 joinForm으로 넘어가기
function joinNext(){
	chk = 0;
	termsChks = document.querySelectorAll('#termsChk');		// 필수 약관 체크 항목 들고오기
	console.log('termsChks = ' + termsChks.length);
	for(i=0; i<termsChks.length; i++){
		if(termsChks[i].checked)	{		// 필수항목 체크 시
			chk++;							// 체크 개수 확인
//			console.log('====='+chk);
		}
	}
	if(chk === termsChks.length){			// 체크 된 갯수가 필수 항목 갯수와 같으면
		document.getElementById('terms').className = '';		// 약관 display none 설정	/ 약관 안나오게
		document.getElementById('joinform').className = 'on';	// joinForm display block 설정 / 조인폼 나오게 한다
	}else{
		alert('필수 이용약관에 동의해 주세요.');		// 필수 체크 안 된 경우 알림창 표시 
	}
}


