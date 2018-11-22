function loadFileAsText() {
    var fileToLoad = document.getElementById("fileToLoad").files[0];

    var fileReader = new FileReader();
    fileReader.onload = function (fileLoadedEvent) {
        document.getElementById('java-code').innerText = fileLoadedEvent.target.result;
    };

    fileReader.readAsText(fileToLoad, "UTF-8");

    compilar(fileToLoad);
}

function compilar(file) {
    document.getElementById('token').innerText = "";
    document.getElementById('tabela').innerText = "";
    document.getElementById('assembly').innerText = "";
    document.getElementById('erroToken').innerText = "";
    document.getElementById('erroVerificacao').innerText = "";
    document.getElementById('erroAssembly').innerText = "";
    var data = new FormData();
    data.append("file", file);
    $.ajax({
        type: "POST",
        url: "/compilar/file",
        enctype: 'multipart/form-data',
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 0,
        success: function (xhr) {
            document.getElementById('token').innerText = xhr.token;
            document.getElementById('tabela').innerText = xhr.verificacao;
            document.getElementById('assembly').innerText = xhr.assembly;
            if (xhr.errorToken != null) {
                document.getElementById('erroToken').innerText = xhr.errorToken;
            }
            if (xhr.errorVerificacao != null) {
                document.getElementById('erroVerificacao').innerText = xhr.errorVerificacao;
            }
            if (xhr.errorAssembly != null) {
                document.getElementById('erroAssembly').innerText = xhr.errorAssembly;
            }
            if (xhr.errorToken != null || xhr.errorVerificacao != null || xhr.errorAssembly != null) {
                $(".modal").modal().show();
            }
        }
    });
}

var inputs = document.querySelectorAll( '.inputfile' );
Array.prototype.forEach.call( inputs, function( input )
{
    var label = input.nextElementSibling, labelVal = label.innerHTML;

    input.addEventListener( 'change', function( e )
    {
        var fileName = e.target.value.split( '\\' ).pop();

        if( fileName )
            label.querySelector( 'span' ).innerHTML = fileName + '  ';
        else
            label.innerHTML = labelVal;

        loadFileAsText();
    });

    // Firefox bug fix
    input.addEventListener( 'focus', function(){ input.classList.add( 'has-focus' ); });
    input.addEventListener( 'blur', function(){ input.classList.remove( 'has-focus' ); });
});