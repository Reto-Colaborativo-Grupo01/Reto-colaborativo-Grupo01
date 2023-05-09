CREATE OR REPLACE TRIGGER EQUIPOS_JORNADA_TRG
BEFORE INSERT OR UPDATE OR DELETE ON EQUIPOS
FOR EACH ROW
DECLARE
V_SPLIT_ESTADO VARCHAR2(10);
V_ID_JORNADA NUMBER;
JORNADA_EXISTENTE EXCEPTION;
BEGIN
  SELECT ESTADO INTO V_SPLIT_ESTADO FROM SPLIT WHERE ID IN (SELECT MAX(ID_SPLIT) FROM JORNADAS);
  IF V_SPLIT_ESTADO = 'CERRADO' THEN
    RAISE JORNADA_EXISTENTE;
    END IF;
EXCEPTION
  WHEN JORNADA_EXISTENTE THEN
    RAISE_APPLICATION_ERROR(-20001, 'No se puede realizar esta operaci�n mientras hay una jornada en curso.');
    WHEN NO_DATA_FOUND THEN 
    NULL;
END;
/*
UPDATE EQUIPOS
SET nombre='MANOLO' WHERE nombre='Porcinos FC';

Error que empieza en la l�nea: 18 del comando -
UPDATE EQUIPOS
SET nombre='MANOLO' WHERE nombre='Porcinos FC'
Error en la l�nea de comandos : 18 Columna : 8
Informe de error -
Error SQL: ORA-20001: No se puede realizar esta operaci�n mientras hay una jornada en curso.
ORA-06512: en "SYSTEM.EQUIPOS_JORNADA_TRG", l�nea 12
ORA-04088: error durante la ejecuci�n del disparador 'SYSTEM.EQUIPOS_JORNADA_TRG'
*/