<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="ko">

<head>

    <meta charset="UTF-8">

    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>코레일 로그인</title>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">



    <style>

        .tabs{



            width: 80%;

            background-color: #fff;

            border: 1px solid #e0e0e0;        

            height: 30px;

            display: flex;



            margin: 0 auto;





        }



        .tabs > div{

                   

                background-color: #fff;

                 border: 1px solid #252323;      

               width:25%;

        }





        .login-container{

           

               margin: 0   auto;

               margin-top: 100px;

        }



        .login-bar{

            height: 100px;

            background-color: #00458C;;

            color: #ffffff;

            padding: 10px;

            text-align: center;

            line-height: 100px;

             text-shadow:

             0px 2px 4px rgba(0, 0, 0, 0.5), /* 아래로 약간의 검은색 그림자 */

             0px 0px 2px rgba(0, 0, 0, 0.3);

 

            font-size: 30px;

            font-weight: bold;



        }

    </style>

</head>

<body>



    <div class="login-bar">

        로그인



    </div>











    <div class="login-container">

     





        <div class="login-form-area">

            <h3>코레일멤버십<br>회원번호로 로그인하세요.</h3>

           



            <form class="login-form"  action="<%=request.getContextPath()%>/login"    method="post">

                <div class="input-group">

                    <input type="text" name="id"  placeholder="회원번호를 입력하세요" class="input-field" required>

                </div>

                <div class="input-group password-group">

                    <input type="password"  name="pw" placeholder="비밀번호를 입력하세요" class="input-field" required>

                    <span class="mouse-input">🖱️ 마우스 입력</span>

                </div>



                <div class="options">

                    <label class="checkbox-container">

                        <input type="checkbox"> 회원번호 저장

                    </label>

                    <span class="login-info">로그인 5회 실패 시 로그인이 제한될 수 있습니다.</span>

                </div>



                <button type="submit" class="login-button">로그인</button>

            </form>



            <div class="footer-links">

                <a href="#">회원번호 찾기</a>

                <span>|</span>

                <a href="#">비밀번호 찾기</a>

                <span>|</span>

                <a href="#">회원가입</a>

            </div>

        </div>

       

        <div class="decoration-icon">

             

        </div>

    </div>

</body>

</html>