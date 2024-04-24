package com.ssepulveda.costi.data

import com.ssepulveda.costi.domain.entity.CostType
import com.ssepulveda.costi.domain.entity.SubTypeCost

fun getDefaultData() = listOf(
    CostType(
        id = 1,
        name = "Supermercado",
        listOf(
            SubTypeCost(
                name = "Almacén"
            ),
            SubTypeCost(
                name = "Verdulería"
            ),
            SubTypeCost(
                name = "Carnicería"
            ),
            SubTypeCost(
                name = "Delivery"
            ),
            SubTypeCost(
                name = "Articulos Limpieza"
            ),
            SubTypeCost(
                name = "S-Otros"
            ),
        )
    ),
    CostType(
        id = 2,
        name = "Gastos Fijos",
        listOf(
            SubTypeCost(
                name = "Luz"
            ),
            SubTypeCost(
                name = "Gas"
            ),
            SubTypeCost(
                name = "Internet"
            ),
            SubTypeCost(
                name = "Tv"
            ),
            SubTypeCost(
                name = "Celular"
            ),
            SubTypeCost(
                name = "Tarjeta Credito"
            ),
            SubTypeCost(
                name = "Subscripciones"
            ),
            SubTypeCost(
                name = "Hogar"
            ),
            SubTypeCost(
                name = "GF-otros"
            ),
        )
    ),
    CostType(
        id = 3,
        name = "Formación",
        listOf(
            SubTypeCost(
                name = "Colegio"
            ),
            SubTypeCost(
                name = "Material escolar"
            ),
            SubTypeCost(
                name = "Libros"
            ),
            SubTypeCost(
                name = "Excursiones"
            ),
            SubTypeCost(
                name = "Cursos"
            ),
            SubTypeCost(
                name = "Matricula"
            ),
            SubTypeCost(
                name = "Uniformes"
            ),
            SubTypeCost(
                name = "F-otros"
            ),
        )
    ),
    CostType(
        id = 4,
        name = "Ocio",
        listOf(
            SubTypeCost(
                name = "Vacaciones"
            ),
            SubTypeCost(
                name = "Eventos"
            ),
            SubTypeCost(
                name = "Deporte"
            ),
            SubTypeCost(
                name = "Restaurantes"
            ),
            SubTypeCost(
                name = "Bares"
            ),
            SubTypeCost(
                name = "Cumpleaños"
            ),
            SubTypeCost(
                name = "O-otros"
            ),
        )
    ),
    CostType(
        id = 5,
        name = "Transporte",
        listOf(
            SubTypeCost(
                name = "Combustible"
            ),
            SubTypeCost(
                name = "Taxi / Bus / tren"
            ),
            SubTypeCost(
                name = "Parqueadero"
            ),
            SubTypeCost(
                name = "T-otros"
            ),
        )
    ),
    CostType(
        id = 6,
        name = "Vivienda",
        listOf(
            SubTypeCost(
                name = "Muebles"
            ),
            SubTypeCost(
                name = "Electrodomésticos"
            ),
            SubTypeCost(
                name = "Reparaciones"
            ),
            SubTypeCost(
                name = "Decoración"
            ),
            SubTypeCost(
                name = "Limpieza"
            ),
            SubTypeCost(
                name = "V-otros"
            ),
        )
    ),
    CostType(
        id = 7,
        name = "Salud",
        listOf(
            SubTypeCost(
                name = "Farmacia"
            ),
            SubTypeCost(
                name = "Cuidado personal"
            ),
            SubTypeCost(
                name = "Gimnasio"
            ),
            SubTypeCost(
                name = "Sa-otros"
            ),
        )
    ),
    CostType(
        id = 8,
        name = "Seguros",
        listOf(
            SubTypeCost(
                name = "Seguro Vivienda"
            ),
            SubTypeCost(
                name = "Jubilacion"
            ),
            SubTypeCost(
                name = "Vehículo"
            ),
            SubTypeCost(
                name = "Vida"
            ),
            SubTypeCost(
                name = "Se-otros"
            ),
        )
    ),
    CostType(
        id = 9,
        name = "Impuestos",
        listOf(
            SubTypeCost(
                name = "Ingresos Brutos"
            ),
            SubTypeCost(
                name = "Riqueza"
            ),
            SubTypeCost(
                name = "Ganancia"
            ),
            SubTypeCost(
                name = "I-otros"
            ),
        )
    ),
    CostType(
        id = 10,
        name = "Mascota",
        listOf(
            SubTypeCost(
                name = "Comida"
            ),
            SubTypeCost(
                name = "Snacks"
            ),
            SubTypeCost(
                name = "Juguetes"
            ),
            SubTypeCost(
                name = "Vacunas"
            ),
            SubTypeCost(
                name = "Veterinario"
            ),
            SubTypeCost(
                name = "M-otros"
            ),
        )
    ),
)