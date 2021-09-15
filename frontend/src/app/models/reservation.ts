import { Client } from "./client";

export class Reservation{
    id:number;
    description:string;
    nbPlaces:number;
    lieu: string;
    dateLimite: Date;

    clients: Client[];
}