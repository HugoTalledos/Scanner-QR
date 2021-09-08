# Scanner-for-GSheets
**Versión:** 0.1 - 
Esta aplicación permite escanear códigos qr, tomando la información y enviándola a una hoja de calculo de google.

Aplicación compuesta por 4 clases adicionales a "MainActivity"

**CameraPermit**
Camara que obtiene los permisos para el uso de la camara
Recibe 3 parametros (CameraSource cameraSource, Context - contexto de aplicación, SurfaceView cameraView) 

**QrDetector**
Clase que contiene el hilo que se encarga del escaneo constante
Recibe 2 parametros (Switch - Ubicado en el main activity encargado de determinar el manejo de la info,  Context - contexto de la aplicación)
En esta clase se prepara la información para ser enviada.

**SendRequest**
Clase que se encarga de realizar la petición al script de Google para la agregación de la info a la hoja de calculo
Recibe 2 parametros (String - Información a enviar, Context - contexto de aplicación)
https://www.google.com/script/start/?authuser=0 - Entrar a esta pagina para generar el script necesario.

**TestHandler**
Clase encargada de notificar al usuario cuando un codigo qr es escaneado.
