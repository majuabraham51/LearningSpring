import React, { Component } from 'react';
import axios from 'axios';
class Login extends Component {
   constructor(props) {
    super(props);
    this.state = {
      username: '',
      password: '',
      grant_type : ''
    };
    this.loadData = this.loadData.bind(this);
  }
   onChange = (e) => {
     const state = this.state
    state[e.target.name] = e.target.value;
    this.setState(state);
  }
  loadData() {
   
    try {
      const Querystring = require('query-string');
      const dataRefresh = {
        grant_type: 'refresh_token',
        username: this.state.username,
        password: this.state.password,
        refresh_token: localStorage.getItem("refresh_token")
      };
        const encodedStringRefresh = new Buffer('maju-client:maju-secret').toString('base64');
        const basicRefresh = 'Basic '+encodedStringRefresh;
        axios.post('/oauth/token', Querystring.stringify(dataRefresh),{headers: {'Content-Type':'application/x-www-form-urlencoded','Authorization':basicRefresh}})
       .then((result) => {
      localStorage.setItem("access_token", result.data.access_token);
      console.log("block maju"+JSON.stringify(result.data));
      });
     
    } catch (e) {
      console.log(e);
      localStorage.clear;
    }
  }

 onSubmit = (e) => {
    e.preventDefault();
      const Querystring = require('query-string');
      const data = {
      grant_type: 'password',
      username: this.state.username,
      password: this.state.password
    };
    const encodedString = new Buffer('maju-client:maju-secret').toString('base64');
    const basic = 'Basic '+encodedString;
    axios.post('/oauth/token', Querystring.stringify(data),{headers: {'Content-Type':'application/x-www-form-urlencoded','Authorization':basic}})
      .then((result) => {
        localStorage.setItem("access_token", result.data.access_token);
        localStorage.setItem("refresh_token", result.data.refresh_token);
       setInterval(this.loadData, 180000);
       console.log("block maju"+JSON.stringify(result.data));
        this.props.history.push("/list")
      });
  }

  render() {
    const { username, password } = this.state;
    return (
      <div class="container">
        <div class="panel panel-default">
          <div class="panel-heading">
            <h3 class="panel-title">
              Login Details
            </h3>
          </div>
           <div class="panel-body">
           
            <form onSubmit={this.onSubmit}>
              <div class="form-group">
                <label for="isbn">User Name:</label>
                <input type="text" class="form-control" name="username" value={username}  onChange={this.onChange} placeholder="User Name" />
              </div>
              <div class="form-group">
                <label for="title">Password:</label>
                <input type="text" class="form-control" name="password" value={password} onChange={this.onChange} placeholder="Password" />
              </div>
              <button type="submit" class="btn btn-default">Submit</button>
            </form>
          </div>
        </div>
      </div>
    );
  }
}

export default Login;
