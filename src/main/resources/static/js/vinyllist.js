// Skripti vastaa taulukon hakutoiminnoista, lajittelusta ja summan laskennasta
// Aja vasta, kun DOM on täysin ladattu
document.addEventListener('DOMContentLoaded', function() {
  // --------------------------------------
  // 1) Perusmuuttujat ja elementtien haku
  // --------------------------------------
  const searchInput   = document.getElementById('searchInput');   // hakukenttä lainausmerkkien perusteella
  const artistFilter  = document.getElementById('artistFilter');  // artistivalitsin dropdown
  const table         = document.getElementById('vinylTable');    // päätaulukko
  const tBody         = table.tBodies[0];                         // taulukon <tbody>
  const rows          = Array.from(tBody.rows);                   // riviolion taulukko
  console.log("Rows count:", rows.length);
  const totalSpan     = document.getElementById('totalValue');    // summaelementti

  // --------------------------------------
  // 2) Suodatusfunktio
  // --------------------------------------
  function filterTable() {
    const text   = searchInput.value.toLowerCase(); // syötteen merkkijono
    const artist = artistFilter.value;              // valitun artistin nimi

    rows.forEach(row => {
      // Kun kuvasarake on 0, nimi on 1 ja artisti 2
      const titleText  = row.cells[1].textContent.toLowerCase(); // casting textiksi
      const artistText = row.cells[2].textContent;              // artistin nimi

      // okText jos haku tai artisti tekstissä
      const okText   = titleText.includes(text)
                     || artistText.toLowerCase().includes(text);
      // okArtist jos ei valintaa tai täsmäys
      const okArtist = !artist || artistText === artist;

      // piilota tai näytä rivi
      row.style.display = (okText && okArtist) ? '' : 'none';
    });

    recalcTotal(); // päivitä summa
  }

  // kuuntele hakukenttää ja dropdownin muutoksia
  searchInput.addEventListener('input',  filterTable);
  artistFilter.addEventListener('change', filterTable);

  // --------------------------------------
  // 3) Summan laskeminen
  // --------------------------------------
  function recalcTotal() {
    let sum = 0;
    const priceIdx = 5; // ostohinta on sarakkeessa 5

    rows.forEach(row => {
      if (row.style.display !== 'none') {
        const txt = row.cells[priceIdx].textContent.trim();
        const val = parseFloat(txt) || 0;
        sum += val;
      }
    });

    // näytä kahden desimaalin tarkkuudella
    totalSpan.textContent = sum.toFixed(2);
  }

  // --------------------------------------
  // 4) Avustaja rivien uudelleenliittämiseen
  // --------------------------------------
  function reappendRows() {
    // tyhjennä tbody
    while (tBody.firstChild) {
      tBody.removeChild(tBody.firstChild);
    }
    // liitä rivit uudessa järjestyksessä
    rows.forEach(r => tBody.appendChild(r));
  }

  // --------------------------------------
  // 5) Sarakeotsikoiden klikkauslajittelu
  // --------------------------------------
  table.querySelectorAll('th').forEach((th, colIndex) => {
    // ohita kuva- ja toimintasarake (0 ja 6)
    if (colIndex === 0 || colIndex === 6) return;

    let asc = true;             // lajittelusuunta, true = nouseva
    th.style.cursor = 'pointer';

    th.addEventListener('click', () => {
      // poista aiemmat sort-luokat kaikista otsikoista
      table.querySelectorAll('th')
           .forEach(h => h.classList.remove('sorted-asc','sorted-desc'));

      // lajittelutaulukko
      rows.sort((a, b) => {
        const cellA = a.cells[colIndex].textContent.trim();
        const cellB = b.cells[colIndex].textContent.trim();

        if (colIndex === 3) {
          // julkaisuvuosi-numeraalisesti
          return asc
            ? parseInt(cellA) - parseInt(cellB)
            : parseInt(cellB) - parseInt(cellA);
        } else {
          // aakkosellinen lajittelu muille sarakkeille
          return asc
            ? cellA.localeCompare(cellB)
            : cellB.localeCompare(cellA);
        }
      });

      // lisää luokka klikattuun otsikkoon
      th.classList.add(asc ? 'sorted-asc' : 'sorted-desc');

      asc = !asc; // käännä lajittelusuunta seuraavaa kertaa varten

      reappendRows(); // päivitä DOM
      filterTable();  // suodatus ja summa päälle
    });
  });

  // --------------------------------------
  // 6) Alkusummaan laskenta
  // --------------------------------------
  recalcTotal();
});
