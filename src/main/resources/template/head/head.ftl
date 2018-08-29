<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width,user-scalable=no">
    <title>Instagram Portfolio</title>
    <link href='http://fonts.googleapis.com/css?family=Lato:400,700|Kaushan+Script|Montserrat' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="/css/style.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script type="text/javascript" src="/js/modernizr.js"></script>
    <script type="text/javascript" src="/js/validador.js"></script>
    <script type="text/javascript">
        $(function() {
            $(".uploadButton").mousemove(function(e) {
                var offL, offR, inpStart
                offL = $(this).offset().left;
                offT = $(this).offset().top;
                aaa= $(this).find("input").width();
                $(this).find("input").css({
                    left:e.pageX-aaa-30,
                    top:e.pageY-offT-10
                })
            });
        });
    </script>

</head>
<body>
