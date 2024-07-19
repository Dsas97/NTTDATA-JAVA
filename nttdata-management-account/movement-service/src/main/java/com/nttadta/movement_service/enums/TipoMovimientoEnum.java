package com.nttadta.movement_service.enums;

import lombok.Getter;

/**
 * Enumeration containing movement types
 */
@Getter
public enum TipoMovimientoEnum {

    DEPOSIT(1, "Depósito", true),
    TAKE(2 , "Retiro", false),
    TRANSFER_RECIVED(3, "Transferencia recibida", true),
    TRANSFER_SENT(4, "Transferencia enviada", false);

    private final Integer id;
    private final String name;
    private final Boolean isIncome;

    /**
     * Class constructor
     * @param id id of the movement type
     * @param name name of the movement type
     * @param isIncome indicates whether the movement is an income
     */
    TipoMovimientoEnum(Integer id, String name, Boolean isIncome) {
        this.id = id;
        this.name = name;
        this.isIncome = isIncome;
    }

    /**
     * Method that allows obtaining the name of the type of movement by id
     * @param id id of the movement type
     * @return name of the move type
     */
    public static Boolean isIncomeById(Integer id){
        for (TipoMovimientoEnum movementTypeEnum : TipoMovimientoEnum.values()) {
            if(movementTypeEnum.getId().equals(id)){
                return movementTypeEnum.getIsIncome();
            }
        }
        return null;
    }


}
