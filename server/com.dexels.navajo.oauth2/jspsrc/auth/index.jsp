<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Bootstrap, from Twitter</title>
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/bootstrap-responsive.css" rel="stylesheet">
    <link href="css/signin.css" rel="stylesheet">

</head>
  <body>

    <div class="container">

      <form class="form-signin" role="form" method="post" action="/oauth/login">
        <h2 class="form-signin-heading">Club site 'De Schoof'</h2>
        <ul>
           <li>Uw gebruikers gegevens</li>
        </ul>
        <input type="text" name="clubcode" class="form-control" placeholder="Club code" required autofocus value="BBKY84H" >
        <input type="text" name="userid" class="form-control" placeholder="Gebruikersnaam" required>
        <input type="hidden" name="clientId" value="client123"/>
        <input type="password"  name="password" class="form-control" placeholder="Wachtwoord" required>
        <label class="checkbox">
          <input type="checkbox" value="remember-me"> Remember me
        </label>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
      </form>

    </div> <!-- /container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
  </body>
</html>