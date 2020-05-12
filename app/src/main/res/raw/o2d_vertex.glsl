uniform mat4 uMVPMatrix;
attribute vec4 aPosition;
attribute vec2 aUV;
varying vec2 vUV;
void main()
{
    gl_Position = uMVPMatrix  *   aPosition;
    vUV =aUV;
}

