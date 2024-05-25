/* eslint-disable @typescript-eslint/no-explicit-any */
import { HTMLAttributeAnchorTarget } from "react";

interface ItemMenuModel{
    label: string,
    icon?: any,
    to: string,
    visible?: boolean;
    disabled?: boolean;
    target?: HTMLAttributeAnchorTarget
}

export interface MenuModel{
    label: string
    items: ItemMenuModel[]
}