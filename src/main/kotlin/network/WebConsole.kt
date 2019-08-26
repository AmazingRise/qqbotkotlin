package network

object WebConsole {
    const val content = """
        <html>
            <head>
                <title>QQ Robot</title>
                <script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
                <script>  
                    $(function(){
                        $.ajax({
                            url: "/exec",
                            type: "get",
                            success: function (returnValue){
                                $("#log_text").val(returnValue);
                                $("#log_text").scrollTop = textarea.scrollHeight;
                            }
                        }
                        )
                    });

                    function on_submit_click(){
                        $.ajax({
                            url: "/exec?"+$("#command").val(),
                            type: "get",
                            success: function (returnValue){
                                $("#log_text").val(returnValue);
                                $("#log_text").scrollTop = textarea.scrollHeight;
                            }
                        });
                        $("#command").val("")
                    }
                </script>
            </head>
            <body>
            <textarea rows="10" cols="50" id="log_text"></textarea>
                    <p>Command:</p>
                    <input type="text" id="command">
                    <button onClick="on_submit_click()">Submit</button>
            </body>
        </html>"""
    var logText = "Welcome to QQ robot console!\n"
}