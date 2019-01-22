import React, {Component} from 'react';
import './App.css';
import ApiService from "../services/ApiService";

class App extends Component {

    apiService = new ApiService();

    state = {
        message: null,
        inputName: ''
    };

    componentDidMount() {
        this.getHello();
    }

    getHello = () => {
        this.apiService
            .getHello()
            .then(this.onHelloLoaded)
            .catch(this.onError);
    };

    onHelloLoaded = (response) => {
        this.setState({
            message: response.message
        });
    };

    onChangeInputName = (e) => {
        this.setState({
            inputName: e.target.value
        });
    };

    onSubmit = (evt) => {
        evt.preventDefault();
        this.apiService
            .postHello(this.state.inputName)
            .then(this.onHelloLoaded)
            .catch(this.onError);
    };

    render() {
        const { message, inputName} = this.state;

        return (
            <form className="App-form d-flex" onSubmit={this.onSubmit}>
                <label className="App-label d-flex">{message}</label>
                <input type="text" className="App-input d-flex" placeholder="Please input your name here"
                       value={inputName} onChange={this.onChangeInputName}/>
                <button type="submit" className="App-btn d-flex">
                    Submit
                </button>
            </form>
        );
    }
}

export default App;
