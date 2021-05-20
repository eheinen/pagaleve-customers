export default {
  type: "object",
  properties: {
    cpf: { type: 'string' },
    email: { type: 'string'},
    name: { type: 'string'},
    phoneNumber: { type: 'string'},
    birthdate: { type: 'string'},
    profession: { type: 'string'},
    salary: { type: 'string'},
    country: { type: 'string'},
    state: { type: 'string'},
    city: { type: 'string'},
    zipcode: { type: 'string'},
    street: { type: 'string'},
    number: { type: 'string'},
    complement: { type: 'string'},
  },
  required: ['cpf', 'email', 'name', 'phoneNumber', 'birthdate', 'profession', 'salary', 'country', 'state', 'city', 'zipcode', 'street', 'number']
} as const;
