import { Korisnik } from "./korisnik";

export interface Log {
  id: number;
  vrijeme: Date;
  akcija: string;
  detalji: string;
  korisnik: Korisnik;
}
