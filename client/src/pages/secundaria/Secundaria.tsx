const Secundaria = () => {
  function imprimeTela() {
    const elementos = [];
    for (let i = 0; i < 1000; i++) {
      elementos.push(<div key={i}>Pg Secundaria {i}</div>);
    }
    return elementos;
  }

  return <>{imprimeTela()}</>;
};
export default Secundaria;
