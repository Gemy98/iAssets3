<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>My Uploadify Implementation</title>
    <link rel="stylesheet" type="text/css" href="js/uploadify/uploadify.css">
    <script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="js/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript">
    $(function() {
        $('#file_upload').uploadify({
            'swf'      : 'js/uploadify/uploadify.swf',
            'fileObjName' : 'files',
            'uploader' : '<%=request.getContextPath()%>/FileUploadServlet'
            // Your options here
        });
    });
    </script>
</head>
<body>
<input type="file" name="file_upload" id="file_upload" />
</body>
</html>