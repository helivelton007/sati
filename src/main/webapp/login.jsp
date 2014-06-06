<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html; charset=UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Gtec Tecnologia Login</title>

<link href="css/bootstrap.css" rel="stylesheet" />
<link href="css/bootstrap-responsive.css" rel="stylesheet" />
<link href="css/styles.css" rel="stylesheet" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control"
	content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<script>
	function focar() {
		document.getElementById("usuario").focus();
	}
</script>
</head>
<body>
	<form action="j_spring_security_check" method="post">
		<div class="wrapper">
			<div class="header">
				<div class="container">
					<div class="row branding">
						<div class="span6">
							<h1 class="pull-left">
								<a href="#"><strong><img src="imagens\LOGOSATI.jpg"></strong></a>
							</h1>
						</div>
					</div>

				</div>
			</div>
			<div class="container content">
				<div class="row">
					<div class="span8 leftContent">
						<h2>Seja Bem Vindo!</h2>
						<div class="row">
							<div class="span4">
								<p class="cntPara simpleDesign" align="justify">
									<strong class="lead">Cadastros</strong> Clientes <br />
									Técnicos<br />Contatos
								</p>
							</div>

							<div class="span4">
								<p class="cntPara itsFree" align="justify">
									<strong class="lead">Cadastro de Equipamentos</strong>
									Equipamentos <br /> Marcas<br />Modelos
								</p>
							</div>
						</div>
						<hr />
						<div class="row">
							<div class="span4">
								<p class="cntPara secureApp" align="justify">
									<strong class="lead">O.S</strong> Cadastro Ordem de Serviço<br />Impressão
									de O.S<br />Consulta de O.S
							</div>

							<div class="span4">
								<p class="cntPara easyUse">
									<strong class="lead">Título caixa 4</strong> conteúdo caixa 4
								</p>
							</div>
						</div>
						<hr />
						<div class="row">
							<div class="span8">
								<h3 class="quickTour">Copyright Gtec Tecnologia</h3>
								Desenvolvedores<br /> Glaicon da Silva Reis<br />Bruno de
								Carvalho<br />Faculdade SENAI FATESG.
							</div>
						</div>
					</div>

					<div class="span4 sidebar">
						<h2>
							<a href="usuario.jsf" class="btn btn-large btn-warning">Cadastre-se</a>
						</h2>
						<div class="well quickSignupForm">
							<h3>Acesso ao Sistema</h3>
							<%
								if (request.getParameter("msg") != null) {
									out.print("<span style='color: red;font-weight: bold;'>Usuário ou senha incorretos</span>");
								}
							%>
							<label>Login</label> <input type="text" id="usuario"
								name="j_username" class="span3" /> <label>Senha</label> <input
								name="j_password" type="password" class="span3" /> <input
								class="btn btn-large btn-success btnSignup" type="submit"
								value="Entrar" />

						</div>
					</div>
				</div>
</body>
</html>