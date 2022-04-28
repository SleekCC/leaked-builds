uniform vec2 resolution;

void main()
{
    vec2 var = (gl_FragCoord.xy / resolution.xy);
    vec3 col=vec3(var.x / 2.0, var.y / 8.0, var.y / 2.0);
    gl_FragColor=vec4(col, 0.8);
}
