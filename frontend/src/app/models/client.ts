import { User } from "./user";
import { Reservation } from "./reservation";

export class Client extends User{
    nomUtilisateur: string;
    adresse: string;

    reservations: Reservation[];
}