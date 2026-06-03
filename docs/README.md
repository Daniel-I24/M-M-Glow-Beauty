# Diagramas de arquitectura - M&M Glow Beauty

| Vista | Archivo |
|-------|---------|
| Contexto | `VistaDeContexto.drawio.png` |
| Funcional | `Vista Funcional.drawio.png` |
| Logica | `Vista Logica.drawio.png` |
| Desarrollo | `vista-desarrollo.puml` + `VistaDesarrollo.png` (unico diagrama actualizado) |
| Despliegue | `Vista de Despliegue .drawio.png` |

## Regenerar vista de desarrollo

```bash
java -jar tools/plantuml.jar -tpng -o docs docs/vista-desarrollo.puml
ren docs\vista-desarrollo-mmglowbeauty.png VistaDesarrollo.png
```

Ver tambien: `DESPLIEGUE-PRODUCCION.md`
