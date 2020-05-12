#extension GL_OES_EGL_image_external : require

precision mediump  float ;

uniform samplerExternalOES   uTexture;
uniform vec4 uColor;

varying vec2  vUV;

void main()
{
    vec4 c =  texture2D( uTexture , vUV );
    gl_FragColor = c * uColor;
}
