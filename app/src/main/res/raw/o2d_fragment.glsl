precision mediump  float;

uniform sampler2D  uTexture;
uniform vec4 uColor;

varying vec2  vUV;

void main()
{
    vec4 c =  texture2D(uTexture, vUV);
    gl_FragColor = c * uColor;
}
