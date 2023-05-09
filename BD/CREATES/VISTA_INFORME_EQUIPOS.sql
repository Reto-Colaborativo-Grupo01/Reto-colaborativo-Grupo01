CREATE OR REPLACE VIEW V_INFO_EQUIPOS AS  
SELECT 
    E.PRESUPUESTO_ANUAL,
    (SELECT NOMBRE FROM PERSONALES P, CONTRATOS_PERSONAL CP 
    WHERE P.ID = CP.ID_PERSONAL AND UPPER(P.OFICIO) = 'ENTRENADOR' AND E.ID = CP.ID_EQUIPO) ENTRENADOR,
    (SELECT NOMBRE FROM PERSONALES P, CONTRATOS_PERSONAL CP 
    WHERE P.ID = CP.ID_PERSONAL AND UPPER(P.OFICIO) = 'PRESIDENTE' AND E.ID = CP.ID_EQUIPO) PRESIDENTE,
    (SELECT COUNT(*) FROM JUGADORES J, CONTRATOS_JUGADORES CJ 
    WHERE J.ID =  CJ.ID_EQUIPO AND E.ID = CJ.ID_EQUIPO) CANTIDADJUGADORES,
    J.NOMBRE NOMBRE_JUGADOR,
    J.APELLIDOS APELLIDO_JUGADOR,
    J.POSICION,
    J.TIPO,
    J.VELOCIDAD,
    J.FISICO,
    J.TIRO,
    J.PASE,
    J.TALENTO,
    J.DEFENSA,
    CJ.SUELDO
FROM EQUIPOS E, CONTRATOS_JUGADORES CJ, JUGADORES J
WHERE E.ID = CJ.ID_EQUIPO AND CJ.ID_JUGADOR = J.ID;

SELECT * FROM V_INFO_EQUIPOS;