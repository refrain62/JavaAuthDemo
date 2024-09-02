<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Struts2 サンプル</title>
</head>
<body>
<h2>Hello World!</h2>

<!-- Googleログインボタン -->
<script src="https://accounts.google.com/gsi/client" async defer></script>
<div id="g_id_onload"
     data-client_id="341206506907-e4pse4vuta154b6rf318ufpnbv3toopf.apps.googleusercontent.com"
     data-login_uri="http://localhost:8080/auth/callback/google"
     data-auto_prompt="false">
</div>
<div class="g_id_signin"
     data-type="standard"
     data-size="large"
     data-theme="outline"
     data-text="sign_in_with"
     data-shape="rectangular"
     data-logo_alignment="left">
</div>

</body>
</html>
