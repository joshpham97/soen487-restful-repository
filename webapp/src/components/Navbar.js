function Navbar() {
    return (
      <div id="navbar" className="mb-2">
          <span>
              <a href="/">Home</a>
          </span>

          <span className="ml-2">
              <a href="/example">Example</a>
          </span>

          <span className="ml-2">
              <a href="/example2">Example2</a>
          </span>

          <span className="ml-2">
              <a href="/albums">Albums</a>
          </span>
      </div>
    );
}

export default Navbar;
