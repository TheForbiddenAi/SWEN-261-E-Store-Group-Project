export interface Cart {
  customerId: number;
  items: { [duckId: number | string]: number };
}