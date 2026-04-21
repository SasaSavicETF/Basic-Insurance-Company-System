import { Korisnik } from './korisnik';
import { Polisa } from './polisa';

export interface Kupovina {
  id: number;
  datumUplate: Date;
  iznos: number;
  korisnik: Korisnik;
  polisa: Polisa;
}
