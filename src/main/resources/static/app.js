'use strict';

import {html, render} from 'https://unpkg.com/htm/preact/index.mjs?module'
import {useEffect, useState} from 'https://unpkg.com/preact/hooks/dist/hooks.mjs?module';

function App() {
    const [category, setCategory] = useState();
    const [name, setName] = useState('');

    const [categories, setCategories] = useState([]);
    const [names, setNames] = useState([]);
    const [note, setNote] = useState('분류와 이름을 선택하고 조회하세요');

    function categorize(path) {
        if (category) {
            return `${window.location.protocol}//${category}.${window.location.host}/${path}`;
        } else {
            return path;
        }
    }

    function fetchJson(path, callback) {
        path = categorize(path);
        axios.get(path)
            .then(callback)
            .catch(err => {
                setNote(`${path} 오류 (${err})`);
                console.error(err);
            });
    }

    function fetchJsonWithToken(path, callback) {
        let tokenPath = categorize('token');
        axios.get(tokenPath)
            .then(r => {
                const token = r.headers.authorization || r.data.accessToken;
                path = categorize(path);
                axios.get(path, {headers: {'Authorization': token}})
                    .then(callback)
                    .catch(err => {
                        setNote(`${path} 오류 (${err})`);
                        console.error(err);
                    });
            })
            .catch(err => {
                setNote(`${tokenPath} 오류 (${err})`);
                console.error(err);
            });
    }

    function onSubmit(e) {
        e.preventDefault();
        console.log(category, name);
        if (name) {
            fetchJsonWithToken(`item?name=${name}`, r => {
                setNote(`${name}의 가격은 ${r.data.price}원입니다`);
            });
        }
    }

    useEffect(() => {
        fetchJson('categories', r => {
            setCategories(r.data);
            setCategory(r.data[0].slug);
        });
    }, []);

    useEffect(() => {
        if (category) {
            fetchJsonWithToken('item', r => setNames(r.data));
        }
    }, [category]);

    return html`
      <form onSubmit="${onSubmit}">
        <dl>
          <dt>분류:</dt>
          <dd>
            <select name="category" value="${category}" onInput=${e => setCategory(e.target.value)}>
              ${categories.map(c => html`
                <option value="${c.slug}">${c.name}</option>
              `)}
            </select>
          </dd>
          <dt>이름:</dt>
          <dd>
            <input type="text" name="name" list="names" autocomplete="off" value="${name}"
                   onInput=${e => setName(e.target.value)}/>
            <datalist id="names">
              ${names.map(n => html`
                <option value="${n}"/>
              `)}
            </datalist>
          </dd>
          <dt>
            <input type="submit" value="조회"/>
          </dt>
          <dt>
            <span>${note}</span>
          </dt>
        </dl>
      </form>
    `;
}

render(html`
  <${App}/>`, document.body)
