import React, { Component } from "react";
import logo from "./logo.svg";
import "./App.css";

class App extends Component {
  constructor(props) {
    super(props);

    this.state = {
      counter: 0,
      error: null
    };
  }
  render() {
    return (
      <div data-test="component-app">
        <h1 data-test="counter-display">
          The counter is currently {this.state.counter}
        </h1>
        {this.state.error && (
          <h3 data-test="error-display">Counter cannot be less than zero</h3>
        )}
        <button
          data-test="increment-button"
          onClick={() =>
            this.setState({ counter: this.state.counter + 1, error: null })
          }
        >
          Increment counter
        </button>
        <button
          data-test="decrement-button"
          onClick={() =>
            this.state.counter > 0
              ? this.setState({ counter: this.state.counter - 1 })
              : this.setState({ error: true })
          }
        >
          Decrement counter
        </button>
      </div>
    );
  }
}

export default App;
